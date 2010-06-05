#! /bin/bash


rm ~/.m2/repository/com/dominikdorn/rest -R
rm ~/.m2/repository/com/dominikdorn/tuwien -R

mvn clean compile install

