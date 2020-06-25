#!/bin/sh
# title             : env.sh
# description       : Shell script to set environment variables
# author            : Neda Peyrone
# create date       : 23-09-2019
# usage             : ./env.sh
#==============================================================================

function newline_at_eof
{
  if [ -z "$(tail -c 1 "$1")" ] ;
  then
    # Newline at end of file
    return 1
  fi

  # No newline at end of file
  return 0
}

# Set current working directory
function set_cwd 
{
  PWD=$(pwd)
  if grep -q "PWD=" .env
  then
    pwdesc=$(echo ${PWD} | sed 's_/_\\/_g')
    sed -e "s/^PWD=.*/PWD=${pwdesc}/g" .env
  else
    CMD="PWD=${PWD}"
    if newline_at_eof .env ; then
      CMD="\n${CMD}"
    fi
    echo "$CMD"  >> .env
  fi
}

# Set UID
function set_uid 
{
  USER=$(id -u)
  if grep -q "UID=" .env
  then
    sed -e "s/^UID=.*/UID=${USER}/g" .env
  else
    CMD="UID=${USER}"
    if newline_at_eof .env ; then
      CMD="\n${CMD}"
    fi
    echo "$CMD"  >> .env
  fi
}

## Main script
set_uid
set_cwd