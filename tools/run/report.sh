#!/bin/bash 

show_help () {
 
  cat <<END

Usage: $0 [category_name [format]]
 
END
  return
 
}

VERSION_REPORT='1.13.4.1'

# minimum requirements
MINIMUM_JAVA=1.4.0 export MINIMUM_JAVA

ulimit -s 2048
 
if [ `expr "$0" : '\(.\)'` = "/" ]; then
        PREFIX=`dirname $0` export PREFIX
else
        if [ `expr "$0" : '\(..\)'` = ".." ]; then
                cd `dirname $0`
                PWD_CMD=`which pwd 2>&1 | grep -v "no pwd in"`
                PREFIX=`$PWD_CMD` export PREFIX
                cd -
	elif [ `expr "$0" : '\(.\)'` = "." ] || [ `expr "$0" : '\(.\)'` = "/" ]; then
                PWD_CMD=`which pwd 2>&1 | grep -v "no pwd in"`
                PREFIX=`$PWD_CMD` export PREFIX
        else
                PWD_CMD=`which pwd 2>&1 | grep -v "no pwd in"`
                PREFIX=`$PWD_CMD`"/"`dirname $0` export PREFIX
        fi
fi

OPENNMS_HOME="@root.install@"

# load libraries
for script in pid_process arg_process build_classpath \
	compiler_setup find_jarfile handle_properties java_lint \
	ld_path version_compare; do
	source $OPENNMS_HOME/lib/scripts/${script}.sh
done

for file in $OPENNMS_HOME/lib/scripts/platform_*.sh; do
	source $file
done

add_ld_path "$OPENNMS_HOME/lib"

CATNAME="$1"; shift
FORMAT="$1"; shift

JAVA_CMD="$JAVA_HOME/bin/java"
APP_CLASSPATH=`build_classpath dir:$OPENNMS_HOME/lib/updates \
	jardir:$OPENNMS_HOME/lib/updates "cp:$CLASSPATH_OVERRIDE" \
	dir:$OPENNMS_HOME/etc jardir:$OPENNMS_HOME/lib  \
	"cp:$CLASSPATH"`
APP_VM_PARMS="-Xmx256m -Dopennms.home=$OPENNMS_HOME -Dimage=@root.install.servlets@/images/logo.gif -Djava.awt.headless=true"
APP_CLASS="org.opennms.report.availability.AvailabilityReport"

if [ -z "$NOEXECUTE" ]; then
	$JAVA_CMD -classpath $APP_CLASSPATH $APP_VM_PARMS -DcatName="$CATNAME" -Dformat="$FORMAT" $APP_CLASS "$@"
	exit 0
fi
