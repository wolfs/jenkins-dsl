package org.jenkinsci.plugins.dsl

/**
 * @author wolfs
 */
class JenkinsConfiguration {

  ProjectFactory projectFactory = new ProjectFactory();

  def projects(Closure closure) {
    closure.delegate = projectFactory;
    closure.call();
  }

}
