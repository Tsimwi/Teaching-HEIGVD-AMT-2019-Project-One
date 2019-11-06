FROM payara/server-full:5.193

MAINTAINER Olivier Liechti <olivier.liechti@heig-vd.ch>

ADD *.war ${DEPLOY_DIR}
ADD drivers/* ${PAYARA_DIR}/glassfish/lib/
ADD scripts/post-boot-commands.asadmin /opt/payara/config/post-boot-commands.asadmin
