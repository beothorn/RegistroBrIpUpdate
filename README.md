RegistroBrIpUpdate
==================

Faz update do ip no dns do registro br se o endereço ip mudar

Uso:

java -jar RegistroBrIpUpdate usuario senha

ou


mvn clean install exec:java -Dexec.args="usuario senha"

O ip é salvo no HOME /.registroBrUpdate/ip.txt, se o ip for diferente do ip no arquivo ele é alterado no site do registroBR
