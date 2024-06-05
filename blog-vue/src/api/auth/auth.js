import request from "@/utils/request";

export default {
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
