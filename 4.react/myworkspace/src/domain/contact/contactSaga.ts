import contactReducer, { addContact, initialContact } from "./ContactSlice";
// ----- saga action을 생성하는 부분

import { createAction, PayloadAction } from "@reduxjs/toolkit";
import { ContactItem } from "./ContactSlice";
import { call, put, takeEvery, takeLatest } from "@redux-saga/core/effects";
import api, { ContactItemRequest, ContactItemResponse } from "./contactApi";
import { AxiosResponse } from "axios";

// contact를 추가하도록 요청하는 action
// {type, payload: ContactItem}
// {type:"contact/requestAddContact", payload: {...}}

// contact를 추가하도록 요청하는 action creator 생성
// createAction<Payload타입>(action.type)
export const requestAddContact = createAction<ContactItem>(
  `${contactReducer.name}/requestAddContact`
);

// contact를 가져오는 action
export const requestFetchContact = createAction(
  `${contactReducer.name}/requestFetchContact`
);

/* ====== saga action을 처리하는 부분 ===== */
// 서버에 POST로 데이터를 보내 추가하고, redux state를 변경
function* addData(action: PayloadAction<ContactItem>) {
  yield console.log("---addData---");
  yield console.log(action);

  // action의 payload로 넘어온 객체
  const contactItemPayload = action.payload;

  //rest api로 보낼 요청객체
  const contactItemRequest: ContactItemRequest = {
    name: contactItemPayload.name,
    phone: contactItemPayload.phone,
    email: contactItemPayload.email,
  };



  // rest api에 post로 데이터 보냄
  // 함수로 호출함(비동기 함수)
  // call(비동기함수, 매개변수1, 매개변수2...)

  // 함수가 Promise를 반환함(비동기 함수)
  const result: AxiosResponse<ContactItemResponse> = yield call(
    api.add,
    contactItemRequest
  );

  // --------2. redux state를 변경함
  // 백엔드에서 처리한 데이터 객체로
  const contactItem: ContactItem = {
    id: result.data.id,
    name: result.data.name,
    phone: result.data.phone,
    email: result.data.email,
    createdTime: result.data.createdTime,
  };

  yield put(addContact(contactItem));

}

// 서버로부터 GET으로 데이터를 가져오고, redux state를 초기화
function* fetchData() {
  yield console.log("--fetchData--");


  const result: AxiosResponse<ContactItemResponse[]> = yield call(api.fetch);

  // ContactItemReponse[] => ContactItem[]
  const contacts = result.data.map(
    (item) =>
    ({
      id: item.id,
      name: item.name,
      phone: item.phone,
      email: item.email,
      createdTime: item.createdTime,
    } as ContactItem)
  );

  yield put(initialContact(contacts));
}

/* ====== saga action을 감지(take)하는 부분 ===== */
export default function* contactSaga() {
  // dispather 동일한 타입의 액션은 모두 처리함
  yield takeEvery(requestAddContact, addData);

  // takeLastest(처리할 액션, 액션을 처리할 함수)
  // 동일한 타입의 액션중에서 가장 마지막 액션만 처리, 이전 액션은 취소
  yield takeLatest(requestAddContact, fetchData);
}



