normal="echo -n -e \e[0m";
blink="echo -n -e \e[5m";
green="echo -n -e \e[42m";
red="echo -n -e \e[41m";
log_file="log.txt";
app="com.example.johan.phonebook";
pkg="com.example.johan.phonebook";
#activity="PlayLocalGameActivity"
activity="ProductListActivity"

if [ $1 = "com" ]; then
    first=1;
    while [ "$2" = "-r" ] || [ $first -eq 1 ]; do
        fecha=`date`;
        echo "">$log_file;
        echo "==${fecha}=======">>$log_file;
        #./gradlew assembleDebug --stacktrace --debug 2>>$log_file 1>>$log_file;
        ./gradlew assembleDebug --stacktrace 2>>$log_file 1>>$log_file;
        r=$?;
        if [ $r -eq 0 ]; then
            $green;
            echo  -n "$fecha: Compilation OK";
            $normal;
            echo;
        else
            $red;
            echo -n "$fecha: Compilation ERROR";
            $normal;
            echo
            echo "ERROR: ";
            cat $log_file | head -50 | grep -e "e:" -e "w:" -C 2;
            echo $fecha;
        fi
        first=0;
        sleep 1s;
    done;
    exit;
fi;
if [ $1 = "viewcolors" ]; then
    for i in `seq 0 256`; do
        echo -e "\e[${i}mTEXTO con valor: ${i}\e[0m";
    done;
        echo -e "\e[31m\e[42mTEXTO con valor: ${i}";
    exit;
fi

if [ "$2" != ""  ]; then
	activity=$2;
fi
echo "activity: $activity";
#exit;
if [ $1 = "run" ]; then
    ./gradlew assembleDebug &&
    ./gradlew installDebug &&
    adb shell cmd package uninstall -k "${app}" &&
    #el anterior siempre da $?=0
    adb -d install "`pwd`/app/build/outputs/apk/debug/app-debug.apk" &&
    #anterior manejarla con $? 0->exito   otro-> fall{o
    adb shell am start -n "${app}/${pkg}.${activity}" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER;
    date;
    exit;
fi;
if [ $1 = "install" ]; then
    adb shell cmd package uninstall -k $app
    #el anterior siempre da $?=0
    adb -d install "`pwd`/app/build/outputs/apk/debug/app-debug.apk" &&
    #anterior manejarla con $? 0->exito   otro-> fall{o
    adb shell am start -n "${app}/${pkg}.${activity}" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER;
    exit;
fi;
if [ $1 = "start" ]; then
#    adb -d install "`pwd`/app/build/outputs/apk/debug/app-debug.apk" &&
    adb shell am start -n "${app}/${pkg}.${activity}"  -a android.intent.action.MAIN -c android.intent.category.LAUNCHER;
#    sleep 1s;
    exit;
fi;

if [ $1 = "log" ]; then
    p=`adb shell ps -A | grep ${app} | awk '{ print $2 }'`
    echo "PID = $p";
    if [ "$p" != "" ]; then
      adb shell logcat --pid=$p | cut -c 32-
    else
      echo "not opened";
    fi;
    exit;
fi;
if [ $1 = "downloaddata" ]; then
      dir="resources/"
      url="http://garbarino-mock-api.s3-website-us-east-1.amazonaws.com/products/"
#      rm -R $dir
#      mkdir $dir;
		products_id=( "0982a08485" "3d77bc3a98" "a20b55dd53" "5b119b7e68" "fac1a6c3d1" "83002e672d" "8f1dcc0c42" "62cb75e2fa" "dfe199bd8c" "f6f8b547a5");
      echo "downloading list, details and reviews (${#products_id[@]} products)";

      curl -s $url | jq '.' > ${dir}list_products.txt
		for (( i=0; i < ${#products_id[@]} ; i=i+1 )) #for from 0 to total elements (cols x rows)
		do
         p=${products_id[$i]}
         curl -s "${url}${p}/" | jq '.' > ${dir}details${p}.txt
         curl -s "${url}${p}/reviews/" | jq '.' > ${dir}reviews${p}.txt
			echo "product id: ${products_id[$i]}"  ;
      done;

      echo;
      echo "downloading images";
      cat "${dir}*.txt"

#      cat resources/*.txt | egrep "\"url\"|\"image_url\"" > ${dir}list_images.txt

      exit
   exit
fi;
if [ $1 = "downloadimages" ]; then
      dir="resources/"
      dirimages=${dir}images
      mkdir $dirimages

      for i in `cat ${dir}list_images.txt`; do
         wget $i;
      done

   exit
fi;

echo "unrecognized option";

exit;




adb install-multiple -r -t /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/dep/dependencies.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_2.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_9.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_0.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_1.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_3.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_4.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_5.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_7.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_6.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_8.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/instant-run-apk/debug/app-debug.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/resources/instant-run/debug/resources-debug.apk
adb shell am start -n "threeinline.alamo.com.threeinlinedos/threeinline.alamo.com.threeinline.MenuActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER


adb install-multiple -r -t /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/dep/dependencies.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_1.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_2.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_0.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_5.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_4.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_3.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_6.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_7.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_9.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/split-apk/debug/slices/slice_8.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/instant-run-apk/debug/app-debug.apk /home/johan/androidprojects/threeinline2/app/build/intermediates/resources/instant-run/debug/resources-debug.apk


-------


D/AndroidRuntime: Shutting down VM
E/AndroidRuntime: FATAL EXCEPTION: main
    Process: threeinline.alamo.com.threeinlinedos, PID: 19308
    java.lang.RuntimeException: Unable to start activity ComponentInfo{threeinline.alamo.com.threeinlinedos/threeinline.alamo.com.threeinline.PlayLocalGameActivity}: java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.Float
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2955)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3030)
        at android.app.ActivityThread.-wrap11(Unknown Source:0)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1696)
        at android.os.Handler.dispatchMessage(Handler.java:105)
        at android.os.Looper.loop(Looper.java:164)
        at android.app.ActivityThread.main(ActivityThread.java:6938)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.Zygote$MethodAndArgsCaller.run(Zygote.java:327)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1374)
     Caused by: java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.Float
        at threeinline.alamo.com.threeinline.PlayLocalGameActivity.updateTableAspectRatio(PlayLocalGameActivity.kt:309)
        at threeinline.alamo.com.threeinline.PlayLocalGameActivity.onCreate(PlayLocalGameActivity.kt:116)
        at android.app.Activity.performCreate(Activity.java:7183)
        at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1220)
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2908)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3030) 
        at android.app.ActivityThread.-wrap11(Unknown Source:0) 
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1696) 
        at android.os.Handler.dispatchMessage(Handler.java:105) 
        at android.os.Looper.loop(Looper.java:164) 
        at android.app.ActivityThread.main(ActivityThread.java:6938) 
        at java.lang.reflect.Method.invoke(Native Method) 
        at com.android.internal.os.Zygote$MethodAndArgsCaller.run(Zygote.java:327) 
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1374) 
