import axios from "axios";

export interface ContactItemResponse {
  id: number;
  name: string;
  phone: string;
  email: string;
  createdTime: number;
}

export interface ContactItemRequest {
  name: string;
  phone: string;
  email: string;

}


// 서버하고 데어터 연동하는 api처리 목록을 별도의 객체로 작성
// process.env.변수명
const contactApi = {
  fetch: () =>
    // axios.get<응답데이터의타입>(응답URL);
    // GET 응답URL HTTP/1.1
    axios.get<ContactItemResponse[]>(`${process.env.REACT_APP_API_BASE}/contacts`),

  // axios.post<응답타임>(요청URL, 요청객체(JSON바디));
  // POST 요청URL HTTP/1.1 {...}
  add: (contactItem: ContactItemRequest) =>
    axios.post<ContactItemResponse>(`${process.env.REACT_APP_API_BASE}/contacts`,
      contactItem
    ),

};

export default contactApi;