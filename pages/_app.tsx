import "../styles/globals.scss";
import type { AppProps } from "next/app";
import Appbar from "../components/Appbar";
import Sidebar from "../components/Sidebar";

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <body>
      <div className="wrap">
        <header className="app-bar">
          <h1>App-Bar</h1>
        </header>
        <nav className="nav-bar">
          <h2>Nav-Bar</h2>
        </nav>
        <article className="contents">
          <Component {...pageProps} />
        </article>
      </div>
    </body>
  );
}

export default MyApp;
