# HealthTracker

HealthTracker là ứng dụng Android hỗ trợ theo dõi lượng calo nạp vào và tiêu thụ hằng ngày. Ứng dụng giúp người dùng thiết lập mục tiêu năng lượng, ghi lại bữa ăn và hoạt động thể chất, đồng thời theo dõi tiến trình qua Dashboard và biểu đồ thống kê.

## Chức năng chính

- Thiết lập và chỉnh sửa hồ sơ sức khỏe cá nhân.
- Tính BMR, TDEE và mục tiêu calo theo giới tính, tuổi, thể trạng, mức độ vận động và mục tiêu cân nặng.
- Tính BMI và hiển thị phân loại tương ứng.
- Ghi nhật ký bốn bữa ăn trong ngày.
- Tìm kiếm món ăn có sẵn hoặc tạo món ăn tùy chỉnh.
- Ghi hoạt động thể chất và tính calo tiêu thụ theo MET.
- Theo dõi calo mục tiêu, đã nạp, đã tiêu thụ và còn lại trên Dashboard.
- Xem biểu đồ calo trong bảy ngày gần nhất và thống kê theo tuần.
- Hỗ trợ tiếng Việt và tiếng Anh.
- Hỗ trợ Light, Dark, System theme, nhiều bảng màu và ba cỡ chữ.

## Công nghệ sử dụng

- Kotlin
- Jetpack Compose và Material 3
- MVVM kết hợp luồng Event, State và Effect
- Kotlin Coroutines, Flow và StateFlow
- Room Database
- DataStore Preferences
- Hilt
- Navigation 3

## Kiến trúc

Source code được chia thành các tầng chính:

```text
presentation  UI, ViewModel, state và effect
domain        Model, repository contract và use case nghiệp vụ
data          Room, DataStore, DAO, mapper và repository implementation
di            Cấu hình dependency injection bằng Hilt
core          Navigation, locale và tiện ích dùng chung
```

Các công thức và quy tắc nghiệp vụ được đặt trong tầng `domain`. UI quan sát dữ liệu thông qua `Flow` để tự cập nhật khi hồ sơ, bữa ăn hoặc hoạt động thay đổi.

## Công thức chính

```text
BMR nam = 10 × cân nặng + 6.25 × chiều cao - 5 × tuổi + 5
BMR nữ = 10 × cân nặng + 6.25 × chiều cao - 5 × tuổi - 161

TDEE = BMR × hệ số vận động

Giảm cân = TDEE - 500 kcal
Giữ cân  = TDEE
Tăng cân = TDEE + 500 kcal

Calo tiêu thụ = MET × cân nặng × thời gian
```

## Cách chạy dự án

1. Mở thư mục dự án bằng Android Studio.
2. Chờ Gradle đồng bộ dependencies.
3. Chọn thiết bị Android hoặc emulator có API 26 trở lên.
4. Chạy module `app`.

## Lưu trữ dữ liệu

Hồ sơ, món ăn, nhật ký bữa ăn và hoạt động được lưu cục bộ bằng Room. Cài đặt theme, bảng màu và cỡ chữ được lưu bằng DataStore. Ngôn ngữ sử dụng cơ chế per-app locale của Android.

Calories của từng meal/activity entry được lưu dưới dạng snapshot tại thời điểm ghi nhật ký. Vì vậy, thay đổi hồ sơ sau này không làm thay đổi calories đã ghi trước đó.

## Giới hạn hiện tại

- Thống kê lịch sử chưa lưu phiên bản hồ sơ theo từng thời điểm. Goal calories của các ngày cũ được tính lại dựa trên hồ sơ hiện tại.
- Chưa triển khai notification nhắc ghi nhật ký, widget và xuất báo cáo PDF.