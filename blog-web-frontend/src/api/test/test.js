import request from '@/utils/request';

export default {
  hello() {
    return request({
      url: '/test/hello',
      method: 'get',
    });
  },
};