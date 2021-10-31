import "../styles/globals.scss";
import type { AppProps } from "next/app";
import Link from "next/link";
import Appbar from "../components/Appbar";
import Sidebar from "../components/Sidebar";

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <body>
      <div className="wrap">
        <header className="app-bar">
          <h1>협업툴 이름(미정)</h1>
        </header>
        <nav className="nav-bar">
          <h2>
            <Link href="/">Main</Link>
          </h2>
          <ul>
            <li>Feeds</li>
            <li>Project</li>
            <li>
              <Link href="/boards">Task Board</Link>
            </li>
            <li>Wiki</li>
          </ul>
        </nav>
        <article className="contents">
          <Component {...pageProps} />
        </article>
      </div>
    </body>
  );
}

export default MyApp;
