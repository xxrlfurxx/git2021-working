// geceric: 제너릭, 제네릭, 쥐네릭
// 타입 매개변수
// 다양한 타입에 따라서 실행 처리를 다르게하기 위함
function identity<Type>(arg: Type): Type {
  return arg;
}

let output1 = identity<string>("Typescript");
let output2 = identity<number>(1);