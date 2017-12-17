#! /bin/sh

d='docker-compose'
name='moyori-jirou'
des="${d} exec sbt"
sbt="${des} sbt"
dep="${d} exec postgis"

case $1 in
  build) $d build;;
  clean) $d stop ; yes | $d rm;;
  re) $d restart;;
  up) $d up -d;;
  sh) $des sh;;
  run) $sbt "runMain mtwtkman.Mj $2";;
  compile) $sbt compile;;
  console) $sbt consoleQuick;;
  psql) $dep psql -U postgres;;
  sbt) $sbt $2;;
  *) $d $1;;
esac
