from celery import chain, group, chord
from celery_demo.tasks import add, callback, on_chord_error

if __name__ == '__main__':

    r = add.delay(4, 4)
    print(add.name)
    print(r)
    print(r.ready())
    # print(r.status)
    # print(r.backend)
    print(r.get(timeout=10))

    print("send chain task")
    add_list = (add.s(2, 2), add.s(4), add.s(8))
    res_chain = chain(add_list)()
    print(res_chain.get())

    # items = (1, 2, 3, 4)
    # cks = add.chunks(items, 2)
    # print(group(cks).get())

    print("send chord task")
    chord_head_list = (add.s(i, i) for i in range(2))
    res_chord = chord(chord_head_list, callback.s())()
    print(res_chord)
    print(res_chord.get(timeout=20))

    print("send chord error task")
    chord_head_list = (add.s(i, i) for i in range(4))
    res_chord = (group(chord_head_list) | callback.s().on_error(on_chord_error.s())).delay()
    print(res_chord)
    print(res_chord.get(timeout=20))
