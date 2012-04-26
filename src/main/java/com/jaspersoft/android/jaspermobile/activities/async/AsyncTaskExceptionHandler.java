/*
 * Copyright (C) 2005 - 2012 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.android.jaspermobile.activities.async;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.jaspersoft.android.sdk.client.async.task.JsAsyncTask;
import com.jaspersoft.android.jaspermobile.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import roboguice.util.Ln;

/**
 * @author Volodya Sabadosh (vsabadosh@jaspersoft.com)
 * @author Ivan Gadzhega
 * @version $Id$
 */
public class AsyncTaskExceptionHandler {

    /**
     *
     * @param task  task in which exception is occurred.
     * @param activity activity in which task throw exception.
     */
    public static void handle(JsAsyncTask task, Activity activity, boolean finishActivity) {
        Exception exception = task.getTaskException();
        if (exception != null) {
            // show error dialog
            if (exception instanceof RestClientException) {
                if (exception instanceof HttpStatusCodeException) {
                    HttpStatus statusCode = ((HttpStatusCodeException) exception).getStatusCode();
                    switch (statusCode) {
                        case BAD_REQUEST:
                            showErrorDialog(R.string.error_http_400, activity, finishActivity);
                            break;
                        case UNAUTHORIZED:
                            showErrorDialog(R.string.error_http_401, activity, finishActivity);
                            break;
                        case FORBIDDEN:
                            showErrorDialog(R.string.error_http_403, activity, finishActivity);
                            break;
                        case NOT_FOUND:
                            showErrorDialog(R.string.error_http_404, activity, finishActivity);
                            break;
                        case INTERNAL_SERVER_ERROR:
                            showErrorDialog(R.string.error_http_500, activity, finishActivity);
                            break;
                        case BAD_GATEWAY:
                            showErrorDialog(R.string.error_http_502, activity, finishActivity);
                            break;
                        case SERVICE_UNAVAILABLE:
                            showErrorDialog(R.string.error_http_503, activity, finishActivity);
                            break;
                        case GATEWAY_TIMEOUT:
                            showErrorDialog(R.string.error_http_504, activity, finishActivity);
                            break;
                    }
                } else {
                    showErrorDialog(exception.getLocalizedMessage(), activity, finishActivity);
                }
                // log the exception details
                Ln.e(exception);
            }  else {
                throw new RuntimeException(exception);
            }
        }
    }

    private static void showErrorDialog(int messageId, Activity activity, boolean finishActivity) {
        showErrorDialog(activity.getString(messageId), activity, finishActivity);
    }

    private static void showErrorDialog(String message, final Activity activity, final boolean finishActivity) {
        // prepare the alert box
        AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
        alertbox.setTitle(R.string.error_msg).setIcon(android.R.drawable.ic_dialog_alert);

        // set the message to display
        alertbox.setMessage(message);

        // add a neutral button to the alert box and assign a click listener
        alertbox.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            // click listener on the alert box
            public void onClick(DialogInterface arg0, int arg1) {
                if (finishActivity) activity.finish();
            }
        });

        alertbox.show();
    }
    
}