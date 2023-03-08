1. Create a sonatype-jira account
2. Create a ticket on the jira-account - Automatically trigger a host action on the sonatype central maven repository
        project -> Community sopport -OSSRH
        Issue type -> new project
        Summary -> Summary of the project
        Description -> Description of the project
        GroupId -> io.github.fushioncode
        Project url -> url of the project remote repository
        SCM URL -> Url of the repository .git

3. Set up requirement
        -> Projects configuration
        -> license 
        -> DistributionManagement
        -> Developer
        -> Source code management
        -> PLUGINS
                -> mavin compiler
                -> Java doc
                -> Maven source plugin
                -> Maven gpg plugin
                -> Maven nexus plugin

4. Configuration in the settings.xml file of .m2 folder
        -> Settings
                -> Server
                -> profile
5. Install gpg program and generate Secret key

LINKS:
https://central.sonatype.org/publish/publish-maven/

https://www.freecodecamp.org/news/how-to-upload-an-open-source-java-library-to-maven-central-cac7ce2f57c/