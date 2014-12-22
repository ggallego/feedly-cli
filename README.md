feedly-cli
==========
[![Build Status](https://travis-ci.org/ggallego/feedly-cli.svg?branch=master)](https://travis-ci.org/ggallego/feedly-cli)

A groovy command line client for [Feedly](http://feedly.com/) inspired on Jarkore works on [Feednix](https://github.com/Jarkore/Feednix).

This tends to be a general tool to manipulate feedly by command line, but I use it as a minimalist podcast (and youtube) downloader for feedly:
- mark your podcasts as 'Saved' category on feedly,
- use this tool to download your 'Saved' podcasts to a Dropbox directory (mine is in a crontab job), 
- favorite your files or podcast directory in your Dropbox Android app,
- use [Android Clean Music Player](https://play.google.com/store/apps/details?id=com.myskyspark.music) to play.

## Install

### Distribution Packages

WIP

### From Source

* `$ gradle uberjar`
* `$ java -jar build/libs/feedly-cli.jar`

## Feedly Sign In Method

Currently using [Feednix](https://github.com/Jarkore/Feednix) method, see [here](https://github.com/Jarkore/Feednix#clarification-on-sign-in-method-please-read).

## Usage

Available options (use -h for help):
* -f,--fileconfig <arg>     Path to config file (default: [feedly.properties](blob/master/src/main/resources/feedly.properties))
* -dev,--devtoken <TOKEN>   Feedly developer token (see feednix usage).
* -uid,--userid <id>        Feedly userid.
* -max,--maxposts <NNN>     Fetch <number> posts from feedly (default: 50)
* -labels                   Fetch labels from feedly.
* -tags                     Fetch tags from feedly.
* -posts <LABEL>            Fetch posts from feedly label.
* -media                    Also download embedded media (mainly podcasts) from posts.
* youtube                   Also download youtube video from posts.
* youtubeaudio              Also extract audio from youtube video from posts.
* -v,--verbose              Output debug messages
* -h,--help                 Prints this help message.
