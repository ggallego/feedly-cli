feedly-cli
==========
[![Build Status](https://travis-ci.org/ggallego/feedly-cli.svg?branch=v0.1)](https://travis-ci.org/ggallego/feedly-cli)

A groovy command line client for [Feedly](http://feedly.com/) inspired on Jarkore works on [Feednix](https://github.com/Jarkore/Feednix).

This tends to be a general tool to manipulate feedly by command line, but I use it as a podcast (and youtube) downloader for feedly:
- mark your podcasts as 'Saved' category,
- use this tool to download your 'Saved' podcasts to a Dropbox directory, 
- favorite your files or podcast directory in your Dropbox Android app,
- use [Android Clean Music Player](https://play.google.com/store/apps/details?id=com.myskyspark.music) to play.

## Install

### Distribution Packages

WIP

### From Source

`gradle uberjar`
`java -jar build/libs/feedly-cli.jar`

## Feedly Sign In Method

Currently using [Feednix](https://github.com/Jarkore/Feednix) method, see [here](https://github.com/Jarkore/Feednix#clarification-on-sign-in-method-please-read).

## Usage

Available options (use -h for help):
 -dev,--devtoken <TOKEN>   Feedly developer token (see feednix usage).
 -f,--fileconfig <arg>     Path to config file (default:
                           feedly.properties)
 -h,--help                 Prints this help message.
 -labels                   Fetch labels from feedly.
 -max,--maxposts <NNN>     Fetch <number> posts from feedly (default: 50)
 -posts <LABEL>            Fetch posts from feedly label.
 -tags                     Fetch tags from feedly.
 -uid,--userid <id>        Feedly userid.
 -v,--verbose              Output debug messages

##Changelog

###v0.1

* list labels, tags and posts from command line
* parse of embedded youtube videos not implemented yet
* podcast/youtube downloader not implemented yet
