package org.jenkinsci.plugins.dsl

import hudson.tasks.BuildTrigger
import jenkins.model.Jenkins
import org.junit.Rule
import org.junit.Test
import org.jvnet.hudson.test.JenkinsRule
import static org.junit.Assert.assertTrue
import hudson.model.FreeStyleProject
import static org.junit.Assert.assertEquals

/**
 * @author wolfs
 */
class DslTest {
  @Rule
  public JenkinsRule j = new JenkinsRule();

  @Test
  public void testProject() {
    Closure closure = {
      projects {
        freestyle("First_Project", {
          blockBuildWhenUpstreamBuilding = true
          publisherList.add(new BuildTrigger("Second_Project", false))
        })

        freestyle "Second_Project"
      }
    }


    def jenkinsConf = new JenkinsConfiguration();
    closure.delegate=jenkinsConf;
    closure.call();

    FreeStyleProject project = Jenkins.getInstance().getItem("First_Project")
    FreeStyleProject secondProject = Jenkins.getInstance().getItem("Second_Project")
    assertTrue(project.blockBuildWhenDownstreamBuilding);
    def buildTrigger = project.getPublishersList().get(BuildTrigger.class)
    assertEquals(buildTrigger.childProjects.iterator().next(), secondProject);
  }
}