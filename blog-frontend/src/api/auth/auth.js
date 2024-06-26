import request from "@/utils/request";

export default {
    /**
     * 获取当前登录状态 data不为空为已登录 为null为未登录
     * @returns
     */
    checkLoginStatus() {
        return request({
            url: "/auth/checkLoginStatus",
            method: "get",
        });
    },
    /**
     * 获取当前登录用户
     * @returns
     */
    getAuthUser() {
        return request({
            url: "/auth/getAuthUser",
            method: "get",
        });
    },
};
