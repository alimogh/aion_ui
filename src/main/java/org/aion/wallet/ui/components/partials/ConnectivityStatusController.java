package org.aion.wallet.ui.components.partials;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.aion.wallet.connector.BlockchainConnector;
import org.aion.wallet.ui.components.AbstractController;
import org.aion.wallet.ui.events.RefreshEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectivityStatusController extends AbstractController {

    private static final String CONNECTIVITY_STATUS_CONNECTED = "CONNECTED";

    private static final String CONNECTIVITY_STATUS_DISCONNECTED = "DISCONNECTED";

    private final BlockchainConnector blockchainConnector = BlockchainConnector.getInstance();

    @FXML
    private Label connectivityLabel;

    @Override
    public void internalInit(final URL location, final ResourceBundle resources) {
    }

    @Override
    protected final void refreshView(final RefreshEvent event) {
        if (RefreshEvent.Type.TIMER.equals(event.getType())) {
            final Task<Boolean> getConnectedStatusTask = getApiTask(o -> blockchainConnector.getConnectionStatusByConnectedPeers(), null);
            runApiTask(
                    getConnectedStatusTask,
                    evt -> setConnectivityLabel(getConnectedStatusTask.getValue()),
                    getErrorEvent(throwable -> {}, getConnectedStatusTask),
                    getEmptyEvent());
        }
    }

    private void setConnectivityLabel(boolean connected) {
        if (connected) {
            connectivityLabel.setText(CONNECTIVITY_STATUS_CONNECTED);
        } else {
            connectivityLabel.setText(CONNECTIVITY_STATUS_DISCONNECTED);
        }
    }
}
