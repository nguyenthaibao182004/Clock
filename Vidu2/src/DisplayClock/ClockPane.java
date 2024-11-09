package DisplayClock;

import Time.CurrentTime;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class ClockPane extends Pane {
    private int hour;
    private int minute;
    private int second;

    // Phương thức constructor khởi tạo thời gian hiện tại
    public ClockPane() {
        this.setClockPaneTime("Asia/Ho_Chi_Minh");
    }

    // Lấy thời gian hiện tại
    public void setClockPaneTime(String zoneID) {
        CurrentTime time = new CurrentTime(zoneID);
        this.hour = Integer.parseInt(time.getHour()) % 12; // hour%12: Đổi từ 24h -> 12h
        this.minute = Integer.parseInt(time.getMinute());
        this.second = Integer.parseInt(time.getSecond());
        this.paintClock();
    }

    // Vẽ đồng hồ
    private void paintClock() {
        // Tính toán kích thước và vị trí của mặt đồng hồ
        double clockRadius = Math.min(this.getWidth(), this.getHeight()) * 0.4;
        double centerX = this.getWidth() / 2;
        double centerY = this.getHeight() / 2;

        // Vẽ nền đồng hồ
        ImageView background = new ImageView(new Image(getClass().getResource("/DisplayClock/Clock.png").toExternalForm()));

        // Đảm bảo ảnh giữ tỉ lệ khi thay đổi kích thước và căn giữa
        background.setPreserveRatio(true);
        background.setFitWidth(this.getWidth());
        background.setFitHeight(this.getHeight());

        // Đưa background vào giữa pane
        background.setTranslateX((this.getWidth() - background.getFitWidth()) / 2);
        background.setTranslateY((this.getHeight() - background.getFitHeight()) / 2);

        this.getChildren().clear();  // Xóa các phần tử cũ trước khi vẽ lại đồng hồ
        this.getChildren().add(background);  // Thêm nền vào

        // Vẽ kim giây
        double secondLength = clockRadius * 0.8;
        double secondEndX = centerX + secondLength * Math.sin(this.second * Math.PI / 30);
        double secondEndY = centerY - secondLength * Math.cos(this.second * Math.PI / 30);
        Line secondLine = new Line(centerX, centerY, secondEndX, secondEndY);
        secondLine.setStroke(Color.RED);
        secondLine.setStrokeWidth(2);

        // Vẽ kim phút
        double minuteReal = this.minute + this.second / 60.0;
        double minuteLength = clockRadius * 0.65;
        double minuteEndX = centerX + minuteLength * Math.sin(minuteReal * Math.PI / 30);
        double minuteEndY = centerY - minuteLength * Math.cos(minuteReal * Math.PI / 30);
        Line minuteLine = new Line(centerX, centerY, minuteEndX, minuteEndY);
        minuteLine.setStroke(Color.BLUE);
        minuteLine.setStrokeWidth(3);

        // Vẽ kim giờ
        double hourReal = this.hour + minuteReal / 60.0;
        double hourLength = clockRadius * 0.5;
        double hourEndX = centerX + hourLength * Math.sin(hourReal * Math.PI / 6);
        double hourEndY = centerY - hourLength * Math.cos(hourReal * Math.PI / 6);
        Line hourLine = new Line(centerX, centerY, hourEndX, hourEndY);
        hourLine.setStroke(Color.GREEN);
        hourLine.setStrokeWidth(4);

        // Thêm các thành phần vào pane (không còn số)
        this.getChildren().addAll(secondLine, minuteLine, hourLine);
    }

    // Mỗi lần thay đổi kích thước cửa sổ nó sẽ lấy chiều rộng và chiều cao để vẽ lại đồng hồ
    @Override
    public void setWidth(double width) {
        super.setWidth(width);
        paintClock();
    }

    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        paintClock();
    }
}
