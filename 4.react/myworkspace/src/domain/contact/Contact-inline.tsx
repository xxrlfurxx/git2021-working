import { useRef, useState } from "react";
import Alert from "../../components/Alert";

import produce from "immer";

interface ContactState {
  id: number,
  memo: string | undefined
  memo1: string | undefined
  memo2: string | undefined
  isEdit?: boolean;
}

const Contact = () => {
  const [contactList, setContactList] = useState<ContactState[]>([]);

  const [isError, setIsError] = useState(false);

  const inputRef = useRef<HTMLInputElement>(null);
  const input1Ref1 = useRef<HTMLInputElement>(null);
  const input1Ref2 = useRef<HTMLInputElement>(null);
  const formRef = useRef<HTMLFormElement>(null);
  const trRef = useRef<HTMLTableRowElement>(null);


  const add = (e: React.KeyboardEvent<HTMLInputElement> | null) => {

    if (e) {
      if (e.code !== "Enter") return;
    }

    if (!inputRef.current?.value) {
      setIsError(true);
      return;
    }

    if (!input1Ref1.current?.value) {
      setIsError(true);
      return;
    }

    if (!input1Ref2.current?.value) {
      setIsError(true);
      return;
    }

    const contact: ContactState = {
      id: contactList.length > 0 ? contactList[0].id + 1 : 1,
      memo: inputRef.current?.value,
      memo1: input1Ref1.current?.value,
      memo2: input1Ref2.current?.value,
    };

    setContactList(
      produce((state) => {
        state.unshift(contact);
      })
    );


    formRef.current?.reset();
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
          item.memo = input?.value;
          item.memo1 = input?.value;
          item.memo2 = input?.value;
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
              {!item.isEdit && <td>{item.memo}</td>}
              {!item.isEdit && <td>{item.memo1}</td>}
              {!item.isEdit && <td>{item.memo2}</td>}
              {item.isEdit &&
                <td><input type="text" defaultValue={item.memo} /></td>
              }
              {item.isEdit &&
                <td><input type="text" defaultValue={item.memo1} /> </td>
              }
              {item.isEdit &&
                <td><input type="text" defaultValue={item.memo2} /> </td>
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