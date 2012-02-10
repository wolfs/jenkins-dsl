package org.jenkinsci.plugins.dsl

import hudson.model.FreeStyleProject
import jenkins.model.Jenkins

/**
 * @author wolfs
 */
class ProjectFactory {

  def freestyle(String name) {
    freestyle(name, {})
  }

  def freestyle(String name, Closure conf) {
    def project = new FreeStyleProject(Jenkins.getInstance(), name);
    conf.delegate = project
    conf.call()
  }

}
