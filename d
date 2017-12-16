#! /bin/sh

d='docker-compose'
name='moyori-jirou'
des="${d} exec sbt"
dep="${d} exec postgis"

case $1 in
  build) $d build;;
  clean) $d stop ; yes | $d rm;;
  re) $d restart;;
  up) $d up -d;;
  sh) $des sh;;
  run) $des run;;
  console) $des consoleQuick;;
  psql) $dep psql -U postgres;;
  *) $d $1;;
esac
