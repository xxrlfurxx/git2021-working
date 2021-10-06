import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import { AppDispatch, RootState } from "../../store";
import { requestFetchContact } from "./contactSaga";

const getTimeString = (unixtime: number) => {
    // 1초: 1000
    // 1분: 60 * 1000
    // 1시간: 60 * 60 * 1000
    // 1일: 24 * 60 * 60 * 1000
    const day = 24 * 60 * 60 * 1000;

    // Locale: timezone, currency 등
    // js에서는 브라우저의 정보를 이용함
    const dateTime = new Date(unixtime);

    // 현재시간보다 24시간 이전이면 날짜를 보여주고
    // 현재시간보다 24시간 미만이면 시간을 보여줌
    return unixtime - new Date().getTime() >= day
        ? dateTime.toLocaleDateString()
        : dateTime.toLocaleTimeString();
};

const Contact = () => {
    const history = useHistory();
    const contact = useSelector((state: RootState) => state.contact);
    const dispatch = useDispatch<AppDispatch>();
    // 실제로는 contact.isFetched가 생성될때와 바뀔때마다 실행됨
    useEffect(() => {
        //   console.log/dispatch);
        if (!contact.isFetchted) {
            dispatch(requestFetchContact());
        }
    }, [dispatch, contact.isFetchted]);

    return (
        <div style={{ width: "60vw" }} className="mx-auto">
            <h2 className="text-center">Contacts</h2>
            <div className="d-flex justify-content-end mb-2">
                <button
                    className="btn btn-secondary me-2"
                    onClick={() => {
                        dispatch(requestFetchContact());
                    }}
                >
                    <i className="bi bi-arrow-clockwise"></i>
                    새로고침
                </button>
                <button
                    className="btn btn-primary"
                    onClick={() => {
                        history.push("/contacts/create");
                    }}
                >
                    <i className="bi bi-plus" />
                    추가
                </button>
            </div>
            <div>
                <table className="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col" style={{ width: "10%" }}>
                                #
                            </th>
                            <th scope="col" style={{ width: "20%" }}>
                                이름
                            </th>
                            <th scope="col" style={{ width: "25%" }}>
                                전화번호
                            </th>
                            <th scope="col" style={{ width: "25%" }}>
                                이메일
                            </th>
                            <th scope="col">작성일시</th>
                        </tr>
                    </thead>
                    <tbody>
                        {contact.data.map((item, index) => (
                            <tr
                                key={`contact-tr-${index}`}
                                onClick={() => {
                                    history.push(`/contacts/detail/${item.id}`);
                                }}
                                style={{ cursor: "pointer" }}
                            >
                                <th scope="row">{contact.data.length - index}</th>
                                <td>{item.name}</td>
                                <td>{item.phone}</td>
                                <td>{item.email}</td>
                                <td>{getTimeString(item.createdTime)}</td>
                            </tr>
                        ))}
                    </tbody>
                    {!contact.data.length && (
                        <tfoot>
                            <tr>
                                <td colSpan={5}>데이터가 없습니다.</td>
                            </tr>
                        </tfoot>
                    )}
                </table>
            </div>
        </div>
    );
};

export default Contact;