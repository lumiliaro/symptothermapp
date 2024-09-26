import { useEffect, useState } from "react";
import ReactMarkdown from "react-markdown";

export default function ChangelogView() {
    const [content, setContent] = useState("");

    useEffect(() => {
        const doFetch = async () => {
            await fetch("CHANGELOG.md")
                .then((res) => res.text())
                .then((text) => setContent(text));
        };

        void doFetch();
    }, []);

    return (
        <div className="post">
            <ReactMarkdown>{content}</ReactMarkdown>
        </div>
    );
}
