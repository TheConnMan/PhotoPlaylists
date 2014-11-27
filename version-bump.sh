#!/bin/bash

# Version Bumper

if [ "$#" -ne 2 ]; then
    echo "Two arguments required, version name and tag message respectively"
else
	echo -e "\nChecking out dev."
    git checkout dev
    
    echo -e "\nEditing application.properties."
    mv application.properties application.properties.temp
    sed "s/app.version=.*/app.version=$1/g" < application.properties.temp > application.properties
    rm application.properties.temp
    
    echo -e "\nCommitting updated version."
    git add application.properties
    git commit -m "Version bump to $1."
    
    git checkout master
    
    git merge --no-ff dev
    
    echo -e "\nTagging version $1."
    git tag -a $1 -m $2
    
    git checkout dev
    
    echo -e "\nSUCCESS"
    echo "To push changes run 'push --all' and 'push --tags'."
fi