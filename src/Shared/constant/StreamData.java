/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared.constant;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class StreamData {

    /* Cách đọc hiểu đống bên dưới?
        => Đống bên dưới được viết comment theo cấu trúc sau:
    
        Tên type // mô tả / dữ liệu gửi đi từ client / dữ liệu trả về từ server
     */
    public enum Type {
        // security
        AESKEY, // client gửi aes key cho server / aeskey đã được mã hóa / server không cần phản hồi

        // auth
        LOGIN, // chức năng đăng nhập / email, password / success hoặc failed
        SIGNUP, // chức năng đăng ký / thông tin đăng ký / success hoặc failed
        LOGOUT, // chức năng đăng xuất / không cần dữ liệu thêm / success hoặc failed

        // main menu
        LIST_ROOM, // lấy danh sách phòng hiện tại / ko cần dữ liệu thêm / dữ liệu danh sách phòng
        LIST_ONLINE, // lấy danh sách người chơi đang online / không cần dữ liệu thêm / dữ liệu danh sách online
        CREATE_ROOM, // chức năng tạo phòng / không cần dữ liệu thêm / success hoặc failed
        JOIN_ROOM, // chức năng vào phòng, nếu chưa có đủ người thì chơi, đủ rồi thì xem / id phòng / success hoặc failed
        WATCH_ROOM, // chức năng vào xem / id phòng / success hoặc failed

        // pair match
        FIND_MATCH, // chức năng tìm trận / không cần dữ liệu thêm / success hoặc failed
        CANCEL_FIND_MATCH, // chức năng hủy tìm trận / không cần dữ liệu thêm / success hoặc failed
        REQUEST_PAIR_MATCH, // chức năng hỏi user có đồng ý ghép cặp không / đồng ý hay không / thông tin user sẽ ghép cặp
        RESULT_PAIR_MATCH, // chức năng gửi thông báo đồng ý ghép cặp / _chỉ có server gửi có client loại type này_ / kết quả ghép cặp

        // in game
        DATA_ROOM, // dữ liệu phòng khi vừa vào phòng / id phòng / dữ liệu phòng
        CHAT_ROOM, // chức năng chat phòng / dữ liệu chat / dữ liệu chat (gửi broadcast trong phòng)
        LEAVE_ROOM, // chức năng thoát phòng / không cần dữ liệu thêm / success hoặc failed

        // profile
        GET_PROFILE, // chức năng xem hồ sơ cá nhân / email user muốn xem thông tin / dữ liệu user
        EDIT_PROFILE, // chức năng chỉnh thông tin cá nhân / thông tin cá nhân mới / success hoặc failed
        CHANGE_PASSWORD, // chức năng đổi mật khẩu / mật khẩu cũ, mật khẩu mới / success hoặc failed

        // game
        GAME_EVENT, //

        // caro game
        START, // chức năng bắt đầu game (khi 1 trong 2 người chơi bắt đầu move) / server gửi broadcast tới room
        MOVE, // chức năng đánh caro tại 1 ô / vị trí ô / success thì broadcast vị trí ô cho mọi người trong phòng, failed khi vị trí không hợp lệ
        UNDO, // chức năng đánh lại / không cần dữ liệu thêm / gửi request broadcast (cho mọi người biết là muốn đánh lại, kể cả viewer)
        UNDO_ACCEPT, // chắc năng đồng ý đánh lại / đồng ý hay không / gửi result broadcast (cho mọi người biết người chơi có đồng ý cho đánh lại hay không)
        NEW_GAME, // chức năng tạo game mới / không cần dữ liệu thêm / nếu game chưa end thì gửi request accept tới đối thủ
        NEW_GAME_ACCEPT, // chức năng đồng ý tạo game mới / đồng ý hay không / gửi result broadcast làm mới trận
        SURRENDER, // chức năng đầu hàng / không có dữ liệu thêm / gửi broadcast
        WIN, // chỉ có server mới gửi type này cho client
        TURN_TICK,
        MATCH_TICK,
        MATCH_TIMER_END, // hết giờ / server gửi tới client
        TURN_TIMER_END, // hết thời gian turn / server gửi tới client

        // specific
        UNKNOW_TYPE, // khi client gửi type không xác định
        EXIT, // chức năng tắt game / không cần dữ liệu thêm / bradcast thoát game
    }

    // https://stackoverflow.com/a/6667365
    public static Type getType(String typeName) {
        Type result = Type.UNKNOW_TYPE;

        try {
            result = Enum.valueOf(StreamData.Type.class, typeName);
        } catch (Exception e) {
            System.err.println("Unknow type: " + e.getMessage());
        }

        return result;
    }

    public static Type getTypeFromData(String data) {
        String typeStr = data.split(";")[0];
        return getType(typeStr);
    }
}
