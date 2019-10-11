keywords = [["muser", "Controller"], ["muser", "Listener"], ["muser", "Facade"]]

def get_left_pad_size(line):
    cnt = 0
    for c in line:
        if c == ' ':
            cnt = cnt + 1
    return cnt


def resolve(path):
    top_list = []
    with open(path) as f:
        pls = -1
        pline = None
        last_line = None
        for line in f:
            last_line = line
            if len(line.strip()) == 0:
                continue
            ls = get_left_pad_size(line)
            if ls <= pls:
                top_list.append(pline.strip())
                pls = -1
                pline = None
            else:
                pls = ls
                pline = line
        if last_line is not None:
            top_list.append(last_line.strip())
    return top_list


for l in sorted(set(filter(lambda x: any(all(k in x for k in kw) for kw in keywords), resolve("D:\\Download\\com.huifu.pos.common.facade.service.JudgeSuccessPaymentFacade.txt")))):
    print(l)











