import { ProxyObjectState } from "immer/dist/internal";
import { useState } from "react";

function Project() {


  return (
    <>
      <div className="mb-2"></div>
      <div style={{ width: "80vw" }} className="mx-auto">
        <h2 className="text-center">프로젝트 목록</h2>
        <div className="d-flex justify-content-end mb-2">
          <button
            className="btn btn-primary"
          >
            <i className="bi bi-plus" />
            생성
          </button>
        </div>
        <div>
          <table className="table table-hover">
            <thead>
              <tr>
                <th scope="col" style={{ width: "5%" }}>
                  No
                </th>
                <th scope="col" style={{ width: "20%" }}>
                  프로젝트명
                </th>
                <th scope="col" style={{ width: "20%" }}>
                  마일스톤
                </th>
                <th scope="col" style={{ width: "15%" }}>
                  시작일
                </th>
                <th scope="col" style={{ width: "15%" }}>
                  종료일
                </th>
                <th scope="col" style={{ width: "15%" }}>
                  PM
                </th>
                <th scope="col" style={{ width: "15%" }}>
                  담당자
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                style={{ cursor: "pointer" }}
              >
                <th scope="row"></th>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>

              </tr>
            </tbody>
            <tfoot>
              <tr>
                <td colSpan={8} style={{ textAlign: "center" }}>생성된 프로젝트가 없습니다.</td>
              </tr>
            </tfoot>
          </table>
        </div>
      </div>
    </>
  );
}

export default Project;
