import request from "@/utils/request";

export default {
  hello() {
    return request({
      url: "/web/test/hello1",
      method: "get",
    });
  },
};
