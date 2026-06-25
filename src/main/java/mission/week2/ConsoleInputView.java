package mission.week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputView {

    private final BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(System.in)
    );

    public String inputPurchaseCount() {
        System.out.print("구매할 로또 수량을 입력해 주세요: ");
        return readLine();
    }

    public String inputTicketNumbers() {
        System.out.println("로또 번호를 입력 해주세요 (예: 1 2 3 4 5 6 ): ");
        return readLine();
    }

    public String retryTicketNumbers() {
        System.out.println("로또 번호를 다시 입력 해주세요 (예: 1 2 3 4 5 6 ): ");
        return readLine();
    }

    private String readLine() {
        try {
            String line = bufferedReader.readLine();
            if (line == null) {
                throw new IllegalStateException("입력이 종료되었습니다.");
            }
            return line;
        } catch (IOException e) {
            throw new IllegalStateException("입력을 읽는 중 오류가 발생했습니다.", e);
        }
    }
}
