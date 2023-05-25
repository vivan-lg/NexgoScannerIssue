#### [Copy nexgo-smartpos-sdk-v3.0.5.aar in libs directory](https://github.com/nexgo/Nexgo-Android-Public/blob/main/Nexgo-Smart-SDK/Library%20and%20Release%20Documentation/nexgo-smartpos-sdk-v3.0.5.aar)

#### Issue1: Camera starts after stop()

Scanner does not stop when scanner.stop() is being called.
When I call scanner.stop() it visually stops camera, but then camera starts again and there's no
reference to this scanner.

STR:

1. Click on "Start scanner" button
2. Scan a barcode in root directory of this repo (pdf417_sample.jpg)

(after successful barcode recognition application calls scanner.stop())

Expected result: camera stops
Actual result: camera stops and then starts again

#### Issue2: Application crashes when back button pressed

STR:

1. Click on "Start scanner" button.
2. Wait until scanner initialized
3. Press back button

Expected result: scanner closed

Actual result: application crashes with

```
java.lang.NullPointerException: Attempt to invoke interface method 'void com.nexgo.oaf.apiv3.device.scanner.OnScannerListener.onInitResult(int)' on a null object reference
                                                                                                    	at com.nexgo.oaf.apiv3.device.scanner.ScannerImpl$1.onServiceConnected(ScannerImpl.java:80)
                                                                                                    	at android.app.LoadedApk$ServiceDispatcher.doConnected(LoadedApk.java:1453)
                                                                                                    	at android.app.LoadedApk$ServiceDispatcher$RunConnection.run(LoadedApk.java:1481)
                                                                                                    	at android.os.Handler.handleCallback(Handler.java:751)
                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:95)
                                                                                                    	at android.os.Looper.loop(Looper.java:154)
                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:6121)
                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
                                                                                                    	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:889)
                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:779)
```