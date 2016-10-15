#! /bin/bash
git add --all .
git commit -m "${1:-"Commit Code"}"
git push origin master
