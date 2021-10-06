import { useEffect, useRef, useState } from "react";
import Alert from "../../components/alert/Alert";

import produce from "immer";

import api from "./contactApi";

interface ContactItemState {
  id: number,
  name: string | undefined
  phone: string | undefined
  email: string | undefined
  isEdit?: boolean;
}

const Contact = () => {
  const [contactList, setContactList] = useState<ContactItemState[]>([]);
  // 데이터 로딩처리 여부를 표시
  const [isLoding, setLoding] = useState<boolean>(true);

  // 에러 여부 state
  const [isError, setIsError] = useState(false);
  const [errMessage, setErrMessage] = useState("");

  const inputRef = useRef<HTMLInputElement>(null);
  const input1Ref1 = useRef<HTMLInputElement>(null);
  const input1Ref2 = useRef<HTMLInputElement>(null);
  const formRef = useRef<HTMLFormElement>(null);
  const trRef = useRef<HTMLTableRowElement>(null);

  // useEffect: 특정조건일 때 작동하는 코드를 작성할 수 있게하는 React Hook
  // React Hook: 클래스 컴포넌트에서만 할 수 있었던 작업을 함수형 컴포넌트에서 사용할 수 있게함 
  // -> 클래스 컴포넌트 state, 컴포넌트 라이프사이클을 처리할 수 있음(stateful)
  // -> 함수형 컴포넌트 다른 컴포넌트로부터 받은 prop으로 화면에 렌더링만(stateless)

  // useEffect(처리할함수, [의존변수])
  // 의존변수일 값/참조가 바뀔때마다 함수가 처리됨
  // ex) props가 바뀌거나 state가 바뀔때 추가적인 처리를 함

  // [] 의존변수목록이 빈 배열 -> 컴포넌트 처음 렌더링되고 마운팅된후에 시점에 처리가됨

  // async: 비동기처리를 할 수 있는 함수
  // 코드가 순차적인 처리가 아닌 다른 컨텐스트에 수행될 수 있도록 함
  // await 키워드는 async 함수 안에서만 사용가능함
  // Promise 객체를 반환하는 함수만 await 키워드를 사용할 수 있음.
  const fetchData = async () => {
    // 백엔드에서 데이터 받아옴
    const res = await api.fetch();

    // axios에서 응답받은 데이터는 data속성에 들어가 있음
    // 서버로부터 받은 데이터를 state 객체로 변환함
    const contacts = res.data.map((item) => ({
      id: item.id,
      name: item.name,
      phone: item.phone,
      email: item.email,
    })) as ContactItemState[];

    setLoding(false); // 로딩중 여부 state 업데이트
    setContactList(contacts); // contact state 업데이트

    console.log("--2. await fetch complete--");
  };

  useEffect(() => {
    console.log("--mounted--");
    // 백엔드에서 데이터를 받아올 것임
    // ES8 style로 async-await 기법을 이용해서 데이터를 조회해옴
    fetchData();
  }, []);

  // await 키워드를 쓰기위해서는 await를 쓰는 함수가 async 메서드로 선언되어야 함
  const add = async (e: React.KeyboardEvent<HTMLInputElement> | null) => {

    if (e) {
      if (e.code !== "Enter") return;
    }

    // 입력값이 없으면 에러 메시지 표시
    if (!inputRef.current?.value) {
      setErrMessage("이름을 입력해주세요");
      setIsError(true);
      return;
    }

    if (!input1Ref1.current?.value) {
      setErrMessage("전화번호를 입력해주세요");
      setIsError(true);
      return;
    }

    if (!input1Ref2.current?.value) {
      setErrMessage("이메일을 입력해주세요");
      setIsError(true);
      return;
    }

    // ----------------백엔드 연동 부분 -------------------
    // try {
    // ...코드 실행부분
    // }
    // catch(e) {
    //   // 코드처리중 에러가 발생하면 실행되는 곳
    //   // e라는 객체는 어떤 에러인지, 에러메시지가 무엇인지를 담고 있음

    // }

    try {
      // const result = await api.add({ memo: inputRef.current?.value, memo1: input1Ref1.current?.value, memo2: input1Ref2.current?.value });
      const result = await api.add({ name: "", phone: "", email: "" });
      console.log(result);
      // ----------------state 변경부분 ---------------------
      // 서버에서 처리한 데이터로 설정
      const contact: ContactItemState = {
        id: result.data.id,
        // optional chaning
        name: result.data.name,
        phone: result.data.phone,
        email: result.data.email,
      };

      setContactList(
        produce((state) => {
          state.unshift(contact);
        })
      );

      // 입력값 초기화
      formRef.current?.reset();
      // 에러 메세지 제거
      setIsError(false);
    } catch (e: any) {
      console.log(e.response);
      // 에러메세지를 표시
      const message = ((e as Error).message);
      setIsError(true);
      setErrMessage(message);
    }
  };

  const del = (id: number, index: number) => {
    console.log(id);

    setContactList(
      produce((state) => {
        state.splice(index, 1);
      })
    );
  };

  const edit = (id: number, mod: boolean) => {

    setContactList(
      produce((state) => {

        const item = state.find((item) => item.id === id);
        if (item) {
          item.isEdit = mod;
        }
      })
    );
  };

  const save = (id: number, index: number) => {
    console.log(trRef.current);

    const input = trRef.current?.querySelectorAll("input")[index];

    setContactList(
      produce((state) => {
        const item = state.find((item) => item.id === id);
        if (item) {
          item.name = input?.value;
          item.phone = input?.value;
          item.email = input?.value;
          item.isEdit = false;
        }
      })
    );
  };


  return (
    <>
      <h2 className="text-center my-5">연락처 관리</h2>
      <form
        className="d-flex"
        ref={formRef}
        onSubmit={(e) => e.preventDefault()}
      >
        <input
          type="text"
          className="form-control me-2"
          placeholder="이름 ..."
          ref={inputRef}
          onKeyPress={(e) => {
            add(e);
          }}

        />
        <input
          type="text"
          className="form-control me-2"
          placeholder="연락처 ..."
          ref={input1Ref1}
          onKeyPress={(e) => {
            add(e);
          }}

        />
        <input
          type="text"
          className="form-control me-2"
          placeholder="이메일 ..."
          ref={input1Ref2}
          onKeyPress={(e) => {
            add(e);
          }}

        />
        <button
          type="button"
          className="btn btn-primary text-nowrap"
          onClick={() => {
            add(null);
          }}
        >
          추가
        </button>
      </form>
      {isError && (
        <Alert
          message={"내용을 입력해주세요."}
          variant={"danger"}

          onClose={() => {
            setIsError(false);
          }}
        />
      )}
      <table className="table table-striped mt-5">
        <thead>
          <tr>
            <th scope="col">#</th>

            <th scope="col">이름</th>

            <th scope="col">전화번호</th>

            <th scope="col">이메일</th>

            <th scope="col">작업</th>

          </tr>
        </thead>
        <tbody>
          {contactList.map((item, index) => (
            <tr ref={trRef} key={item.id}>
              <td>#</td>
              {!item.isEdit && <td>{item.name}</td>}
              {!item.isEdit && <td>{item.phone}</td>}
              {!item.isEdit && <td>{item.email}</td>}
              {item.isEdit &&
                <td><input type="text" defaultValue={item.name} /></td>
              }
              {item.isEdit &&
                <td><input type="text" defaultValue={item.phone} /> </td>
              }
              {item.isEdit &&
                <td><input type="text" defaultValue={item.email} /> </td>
              }
              <td>
                {!item.isEdit && (
                  <button
                    className="btn btn-outline-secondary btn-sm ms-2 me-1 text-nowrap"
                    onClick={() => {
                      edit(item.id, true);
                    }}
                  >
                    수정
                  </button>
                )}
                {!item.isEdit && (
                  <button
                    className="btn btn-outline-secondary btn-sm text-nowrap"
                    onClick={() => {
                      del(item.id, index);
                    }}
                  >
                    삭제
                  </button>
                )}
                {item.isEdit && (
                  <button
                    className="btn btn-outline-secondary btn-sm ms-2 me-1 text-nowrap"
                    onClick={() => {
                      save(item.id, index);
                    }}
                  >
                    저장
                  </button>
                )}
                {item.isEdit && (
                  <button
                    className="btn btn-outline-secondary btn-sm text-nowrap"
                    onClick={() => {
                      edit(item.id, false);
                    }}
                  >
                    취소
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>

      </table>
    </>
  );
};

export default Contact;