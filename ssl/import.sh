#!/bin/sh

sudo keytool -trustcacerts -keystore /home/tsimwi/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/192.7142.36/jbr/lib/security/cacerts -storepass changeit -alias payara -import -file ~/Semestre_5/AMT/Teaching-HEIGVD-AMT-2019-Project-One/ssl/payara-self-signed-certificate.crt
