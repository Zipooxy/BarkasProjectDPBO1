package Service;

import Model.Electronic;
import Model.Product;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HistoryServiceTest {

    @Test
    void addHistoryRecordsEntryThatAppearsInShowHistory() {
        HistoryService historyService = new HistoryService();
        Product item = new Electronic(1, "Laptop", 5000000, "Laptop");

        historyService.addHistory("Membeli", item);

        String output = captureOutput(historyService::showHistory);
        assertTrue(output.contains("Membeli: Laptop"));
    }

    @Test
    void showHistoryReportsEmptyMessageWhenNoEntries() {
        HistoryService historyService = new HistoryService();

        String output = captureOutput(historyService::showHistory);

        assertTrue(output.contains("Belum ada history"));
    }

    @Test
    void tambahHistoryRecordsEntryThatAppearsInLihatHistory() {
        HistoryService historyService = new HistoryService();
        Product item = new Electronic(1, "Laptop", 5000000, "Laptop");

        historyService.tambahHistory("Menjual", item);

        String output = captureOutput(historyService::lihatHistory);
        assertTrue(output.contains("Menjual: Laptop"));
    }

    private String captureOutput(Runnable action) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            action.run();
        } finally {
            System.setOut(original);
        }
        return out.toString();
    }
}
