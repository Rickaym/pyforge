if __name__ == "__main__":
    import os

    from markdown import markdown
    from xhtml2pdf import pisa

    root = ".info/courseboard/"
    html = ""
    for file in os.listdir(root):
        if file.endswith(".md"):
            with open(root + file, "r", encoding="utf-8") as fp:
                text = fp.read()
            html += markdown(text)

    with open("coursebook.pdf", "w+b") as fp:
        pisa.CreatePDF(html, fp)
