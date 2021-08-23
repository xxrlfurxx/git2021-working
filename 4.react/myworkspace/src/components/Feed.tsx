import { useRef, useState } from "react";

// 1건에 대한 타입
interface FeedState {
  id: number;
  memo: string | undefined;
  createTime: number;
  modifyTime?: number;
  isEdit?: boolean;
}

const getTimeString = (unixtime: number) => {
  const dateTime = new Date(unixtime);
  return `${dateTime.toLocaleDateString()} ${dateTime.toLocaleTimeString()}`;
};






  const Feed = () => {
    
    return (
      <>
        <form
          className="mt-5"
          onSubmit={(e) => {
            e.preventDefault();
          }}
        >
          <textarea
            className="form-control mb-1 w-100"
            placeholder="Leave a post here"
            
          ></textarea>
          <div className="d-flex">
            <input
              type="file"
              className="form-control me-1"
              accept="image/png, image/jpeg, video/mp4"

            />
            <button className="btn btn-primary text-nowrap"
              type="button"
             
            >
              입력
            </button>
          </div>
        </form>
      </>
    );
  };

  export default Feed;