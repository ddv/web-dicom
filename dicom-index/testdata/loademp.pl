package TT2UTIL::DB;

use strict;
use DBI;


my $dbh =  DBI->connect( 'dbi:Oracle:medinfo','DICOM_USER','EPy8jC5l');
my $sth = $dbh->prepare('select * from  lpu.v_doctor v') || die $dbh->errstr;

$sth->execute() || die $dbh->errstr;

while(my $row = $sth->fetchrow_hashref){
	print $row->{'ID'} . ';' . $row->{'FULL_NAME'} . ';' .$row->{'END_DATE'} . "\n";
}				      





