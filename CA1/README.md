# Technical Report for Class Assignment 1

Introduction


This technical report documents the development of the Class Assignment 1 (CA1), which encompasses the implementation of version control using Git's best practices. This assignment was also implemented using Subversion as an alternative system control to Git. The tasks here described can be divided into two parts: part 1  which revolved additions on the master branch, and the second part, which revolves on the addition of additional features and fixes with the use of additional branches.

## Part 1: Direct Work on Master Branch

The initial goal revolved around implementation of the setup and the addition of a new feature on the main/master branch.

### Implementation

#### Setup and Initial Commit

1.1.1. After creation of the project's folder, initialize the Git repository, by using the following command (which will add a .git directory to the current project):
   ```bash
   git init
   ```

1.1.2 Afterwards we will clone the tutorialReactSpringRest (folder and contents) in the project's directory:

      ```bash
      git clone git@github.com:spring-guides/tut-react-and-spring-data-rest.git
      ```


1.1.3. We will move the files in the folder to the staging area, to then be commited. Before a commit, the proposed changes need to be added to the staging area (the "." includes all the currently unstaged files in the local directory will be staged):
   ```bash
   git add .
   ```

1.1.4. Commit the added files: this creates a new commit containing the files that were added to the staging area, and the corresponding log message (after the "-m") describing the changes made:
   ```bash
   git commit -m "[Initial] #1 - Initial commit with the moved tut-react-and-spring-data-rest application in the CA1 folder. close #1"
   ```

1.1.5. Now we will push the commit to the remote repository. It's important to use the first command in the case in which the local repository is not linked to the remote one. Other, the second command can be used to upload the files to the remote repository:
   ```bash
   git remote add origin <https://github.com/RicardoAraujoSwitch/devops-23-24-RFA-1231856.git>
   git push origin master
   ```

#### Version Tagging

1.1.6. We will add a tag to the initial version of the application (`v1.1.0`) and push it to the remote repository:
   ```bash
   git tag v1.1.0
   git push origin v1.1.0
   ```

#### New Feature - Addition of Employee Job Years

1.2.0. Firstly, we will need to create issues to signal and document changes in the project. Each issue addresses a specific feature, bug, fix, documentation, etc. The issues are numbered, which allows for the linking between commits in IDE and the remote repository. When linking issues to commits, the issue number can be included in the commit message (e.g., "close #1"). This practice helps in tracking the progress and addressing issues directly from commit messages, providing a clear traceability of changes made in response to specific issues.

1.2.1. The implementation of Employee Title implies changes to the Class and respective Test Class. Having completed the correct implementations in the code, we should add the commit with the relevant changed files:

   ```bash
   git add .
   git commit -m "[Feat] - Added JobTitle field, EmployeeTest class with tests, and updated DatabaseLoader class with an additional field. Close #3"
   ```


1.2.2. We will repeat the process for jobyears. Having completed the correct implementations in the code, we should add the commit with the relevant changed files:

   ```bash
   git add .
   git commit -m "[Feat] Added JobYears field, EmployeeTest class with tests, and updated DatabaseLoader class with an additional field. Close #2"
   ```

1.2.3. We will then push both the commit and the add and push a tag, which will help other contributors/users to understand the current version/status of the added changes, confirming the completion of the task. We've also going to solve an issue related to addition of a .gitignore file (for purposes of denoting files that will not be tracked/added to the remote repository, as it wasn't present in the repository) and the removal of unused/unnecessary files from the remote repository. This will also be linked to another issue "Remove .idea files and added .gitignore", and thus, will give rise to an additional commit "[Feat] Updated .gitignore file and location. Close #4"

   ```bash
   git tag v1.2.0
   git push
   git tag ca1-part1
   git push origin ca1-part1
   ```


## Part 2: Application of Branches for Feature Implementation and Bug Fixes

Goals

By default, we start with a main/master branch, which will serve as the base/stable version of the project. It is pertinente to use additional branches for development and bug fixing, as to prevent disruption of the project before we can make sure that the merge between branches.

### Implementation

#### Feature Development - Addition of Email Field

2.1.1 We will once again create issues in the remote repository that will document the problem/issue/implementation to be applied. In this case, we can associate the issues to the specific branch we're working on it. This will be useful further on, once we have to merge the repository.

2.1.2. We will now create and move to the new `email-field` branch. The checkout command will change the branche under work (along with the "-b", which will create said branch if it doesn't exist).

   ```bash
   git checkout -b email-field
   ```

2.1.3. Just like we've done previously, we will implement the feature (and respective tests), and add, commit, and push the changes in the relevant branch that we're working on (email-field):

   ```bash
   git add .
   git commit -m "[Feat] - Added email field, updated respective tests in EmployeeTest, and updated DatabaseLoader class with the additional field. Close #5"
   git push origin email-field
   ```

2.1.4. Now we will merge this branch into the master branch. If everything was done correctly, and any conflict analysed and dealt with, we should have the updated new  version:

   ```bash
   git checkout master
   git merge email-field
   git tag v1.3.0
   git push origin master
   git push origin v1.3.0
   ```

#### Bug Fix - Validate Email

2.2.1. We will create the issue related to the change we're about to implement "Create a branch to fix invalid email. The system should only accept Employees with valid email (containing "@")".

2.2.2. Firstly, as we've done wit the previous feature, we need to create and switch to appropriate branch:

   ```bash
   git checkout -b fix-invalid-email
   ```

2.2.3 After implementing the changes, adding the tests, we will once again add, commit, and push the changes:

   ```bash
   git add .
   git commit -m "[Feat] - Added email validation regarding whether the email contains "@" sign, with respective tests. Close #6"
   git push origin fix-invalid-email
   ```

2.2.4. We will once again merge the bug fix into the master branch and add the tag:

   ```bash
   git checkout master
   git merge fix-invalid-email
   git tag v1.3.1
   git push origin master
   git push origin v1.3.1
   ```

2.2.5. Document the  completion of part 2 of the assignment:

   ```bash
   git tag ca1-part2
   git push origin ca1-part2
   ```

## Alternative Version Control System Analysis

Subversion Version Control

Subversion (SVN) is a centralized version control system that offers an alternative approach to Git's decentralized model. While Git allows each developer to have a full copy of the entire repository, SVN operates through a central server. This centralization means that all changes are made directly to a single repository, which can streamline certain aspects of version control but also requires a different workflow (due to the need to work constantly with the single repository, contrary to git's approach).

Comparison to Git

    Centralized vs. Distributed: SVN's centralized nature simplifies the process of tracking the project's history by having a single source of code. This approach makes the access, control and history tracking more straightforward. However, unlike Git, SVN requires an active connection to the central repository for most operations, which could be a drawback for developers that are working offline.

    Branching and Merging: In SVN, branching and merging are supported but tend to be more complex compared to Git. Git's design makes branching a core part of the development workflow, facilitating the use of branches for even minor changes, such as bug fixes as we've done in the email fix. SVN treats branches as directory copies within the repository, which can lead to a heavier and less agile use of branches.

    Handling of Binary Files: SVN is known for its efficient handling of binary files, storing only the differences between versions, which can be a significant advantage for projects with large binary assets. Git, on the other hand, stores the full version of binary files on each change, which can increase repository size. Although this didn't occur in the Class 1 Assignment, it would be visible if we tried to constantly add changes to a image, or pdf: while SVN would only record the differences of the file, but git would end up adding also the registry of the different versions, increasing the size of the repository.

## Implementing the Assignment Goals with SVN

# Implementing Class Assignment 1 with SVN involves a series of steps aligned with SVN's centralized model:

1. Initial Setup

   Create SVN Repository: Start by creating a central SVN repository that will host the project files.

     ```bash

     svnadmin create /path/to/svn/repository

2. Check Out the Repository: We can then check out the repository to our local machines, creating a working copy where we can make changes.

    ```bash

    svn checkout http://svn.server.com/path/to/svn/repository

3. Direct Work on Trunk

   Adding Files and Initial Commit: After checking out the repository, add the project files to the working copy and commit them to the central repository.

     ```bash

cd myproject
svn add .
svn commit -m "Initial setup with project files."

4. Tagging Releases: Use SVN's copy mechanism to tag specific snapshots of the project, effectively marking different release versions.

    ```bash

    svn copy http://svn.server.com/path/to/svn/repository/trunk \
    http://svn.server.com/path/to/svn/repository/tags/release-1.0 \
    -m "Tagging release 1.0."

5. Using Branches for Development

   Feature Development: For adding new features or fixing bugs, we will create branches as copies of the trunk. The Work on these branches will be isolated from the main project line.

    ```bash
    svn copy http://svn.server.com/path/to/svn/repository/trunk \
    http://svn.server.com/path/to/svn/repository/branches/feature-x(employee field/test/email, etc.) \
    -m "Starting work on feature (.eg.: email field)."

Merging Back to Trunk: Once feature development or bug fixing is complete, we will merge the changes back into the trunk to update the main project line.

 ```bash

    cd path/to/trunk
    svn merge http://svn.server.com/path/to/svn/repository/branches/feature-x
    svn commit -m "Integrated email field into the main project."

Conclusion

Adopting SVN for Class Assignment 1 requires adjustments to accommodate its centralized version control approach. While SVN might offer simpler history tracking and efficient handling of binary files, it also introduces challenges such as the need for constant server connectivity and a more cumbersome branching process. The choice between SVN and Git ultimately depends on project needs, team preferences, and specific workflow requirements.