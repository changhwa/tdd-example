import sweetalert from 'sweetalert';
import 'sweetalert/dist/sweetalert.css';

export default class Util {

  constructor() {
  }

  static getSuccessNoti() {
    sweetalert(
      {
        title: '성공하였습니다.',
        text: '1초뒤에 닫힙니다.',
        timer: 1000,
        type: 'success',
        showConfirmButton: false
      }
    );
  }
}
