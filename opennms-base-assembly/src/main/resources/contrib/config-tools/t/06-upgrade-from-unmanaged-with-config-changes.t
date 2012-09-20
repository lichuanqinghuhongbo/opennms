use strict;
use warnings;

$|++;

#use Test::More tests => 8;
use Test::More qw(no_plan);

use Carp;
use Cwd qw(abs_path);
use File::Copy;
use File::Path;
use File::Slurp;
use Git;

unshift(@INC, 't');

BEGIN {
	use_ok('OpenNMS::Config');
	use_ok('OpenNMS::Config::Spec');
	use_ok('OpenNMS::Config::RPM');
};

require('common.pl');

my ($rpmroot) = setup_rpmroot ();
my $config = OpenNMS::Config->new(File::Spec->catdir($rpmroot, 'opt', 'opennms'));

# create the init RPM first
my $init = build_rpm("t/rpms/feature-init.spec", "target/rpm/init");
is(@$init, 1);
$init = $init->[0];

# create the first feature RPM
my $rpm09 = build_rpm("t/rpms/feature-a-0.9-1.spec", "target/rpm/a-0.9");
is(@$rpm09, 1);
$rpm09 = $rpm09->[0];

# create the upgrade feature RPM
my $rpm10 = build_rpm("t/rpms/feature-a-1.0-1.spec", "target/rpm/a-1.0");
is(@$rpm10, 1);
$rpm10 = $rpm10->[0];

# install the first rpm on a clean system
OpenNMS::Config::RPM->install(rpms => [ $rpm09 ], root => "target/rpmroot");
ok(-e "target/rpmroot/opt/opennms/etc/testfile.conf", "clean install - testfile.conf must exist");
is(read_file("target/rpmroot/opt/opennms/etc/testfile.conf"), "o-test-feature-a-0.9-1\n\n\n", "clean install - testfile.conf must contain package name");

write_file('target/rpmroot/opt/opennms/etc/testfile.conf', { append => 1}, "New stuff!\n");

# install the upgrade rpm and the init package
OpenNMS::Config::RPM->install(rpms => [ $init, $rpm10 ], root => "target/rpmroot");
ok(-e "target/rpmroot/opt/opennms/bin/config-tools/opennms-post.pl", "clean install - check for opennms-post.pl");
is(read_file("target/rpmroot/opt/opennms/etc/testfile.conf"), "o-test-feature-a-1.0-1\n\n\nNew stuff!\n", "clean install - testfile.conf must contain package name");
ok(-d "target/rpmroot/opt/opennms/etc/.git", "clean install - .git directory must exist");
