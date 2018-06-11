package lovely.zzyfgasstation.manager;

import com.ums.upos.sdk.exception.CallServiceException;
import com.ums.upos.sdk.exception.SdkException;
import com.ums.upos.sdk.printer.PrinterManager;

/**
 * 打印 类
 */
public class PrintManager extends PrinterManager {
    private static final String TGA = "PrintManager";
    private static PrinterManager mPrinterManager;

    public PrintManager() {
    }

    public static synchronized PrinterManager getInstance() {
        if (mPrinterManager == null) {
            mPrinterManager = new PrinterManager();
        }
        return mPrinterManager;
    }

    //打印机初始化
    @Override
    public int initPrinter() throws SdkException, CallServiceException {
        return super.initPrinter();
    }
}
