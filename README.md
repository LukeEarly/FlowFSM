# FlowFSM
<h2>Usage</h2>
To use in your project, download <a href = https://github.com/LukeEarly/FlowFSM/raw/master/out/production/FlowFSM/FlowFSM.jar>this .jar file</a> and incorporate it into your project.

<h2>Contributing</h2>
I built this in IntelliJ IDEA with Java 9. To contribute, <a href = https://help.github.com/articles/fork-a-repo/>fork the repository</a> and make your changes. I included 2 batch files to help compile the source code and create a .jar file on Windows. Make sure to update the output code and the .jar before adding, committing, and requesting a pull request. 
For example, after forking the repo, on Windows:

```cmd
C:\projects> mkdir FlowFSM
C:\projects> cd FlowFSM
C:\projects\FlowFSM> git init
C:\projects\FlowFSM> git remote add origin git@github.com:YourUserName/FlowFSM.git
                                      # OR https://github.com/YourUserName/FlowFSM.git
C:\projects\FlowFSM> git pull origin
                # make changes to source code
C:\projects\FlowFSM> exportJar.bat
C:\projects\FlowFSM> git add .
C:\projects\FlowFSM> git commit -m "Make some changes"
C:\projects\FlowFSM> git push -u origin master
```

Then go to my original repo and <a href = https://help.github.com/articles/creating-a-pull-request/>create a pull request</a> with your fork. 
