import asyncio
import aiohttp

async def fetch_page(session, url):
    response = await session.get(url)
    if response.status == 200:
        text = await response.text()
        print(text)

loop = asyncio.get_event_loop()
session = aiohttp.ClientSession(loop=loop)

tasks = [
    asyncio.ensure_future(
        fetch_page(session, "http://bigsec.com/products/redq/")),
    asyncio.ensure_future(
        fetch_page(session, "http://bigsec.com/products/warden/"))
]
loop.run_until_complete(asyncio.wait(tasks))
session.close()
loop.close()