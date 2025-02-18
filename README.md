# Book Library

## Problem

I use a Personal Knowledge Manager ([Obsidian](https://obsidian.md/)). I have many vaults.
I read books and make same anchors to them along the time (sometimes) in many vaults in many notes.
I want to have the possibility to open such a book from a note,
but I don't want to have it duplicated in every vault, where I mention it.
I want to have many vaults, many anchors in many notes, and I want to have only one book in my file system.
From any point, from any application - I want to open the given book.

## Solution Description

* Auxiliary application for document integration for Personal Knowledge Manager ([Obsidian](https://obsidian.md))
* It enables opening file (book, image, etc.) in its default application located in library from any point in system:
  * from browser
  * form obsidian vault
  * from run command (to test)
* Functionality provided by two application: client and server
* Local machine usage only
* Server calculates a md5 hash for every file in library from its content. I have a solid id, I can rely on. Even if I
  change the folder structure, or file name.

## Technical Specification

* Java version: 21
* MS Windows ready

### Server

* Spring Boot 3 application
* Server builds database of all files from given book library folder (and subfolders)
* Book entry consists of two pieces of information: md5 of the content and full file path
* Usage as windows service via

    java -DLOG_HOME=<log_folder> -Dspring.profiles.active=prod -jar booklibraryserver.jar

### Client

* simple java application (jar with dependencies - fat jar)
* client is launched via custom protocol hook

    java -DLOG_HOME=<log_folder> -jar booklibraryclient.jar booklibraryclient://<book_md5> 

## Toolset

* [Obsidian](https://obsidian.md)
* [Windows Service Wrapper](https://github.com/winsw/winsw)

## Usefull Links
* https://www.aegissofttech.com/articles/how-to-build-a-fatjar-using-maven-in-java.html
* https://www.baeldung.com/slf4j-with-log4j2-logback
* https://www.baeldung.com/java-write-to-file
* https://www.baeldung.com/apache-httpclient-vs-closeablehttpclient
