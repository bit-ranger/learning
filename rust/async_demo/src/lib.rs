#[cfg(test)]
mod tests {
    use async_std::task;

    #[test]
    fn work() {
        task::block_on(async {
            println!("Hello, world!");
        })
    }
}

#[cfg(test)]
mod channel {
    use std::time::Duration;

    use async_std::fs::File;
    use async_std::prelude::*;
    use async_std::sync::channel;
    use async_std::task;

    #[test]
    fn work1() {
        task::block_on(async {
            let (mut s, r) = channel(2);

// This call returns immediately because there is enough space in the channel.
            s.send(1).await;
            s.send(2).await;

            task::spawn(async move {
                // This call blocks the current task because the channel is full.
                // It will be able to complete only after the first message is received.
                s.send(3).await;
            });

// Receive items from the channel
            task::sleep(Duration::from_secs(1)).await;
            assert_eq!(r.recv().await, Some(1));
            assert_eq!(r.recv().await, Some(2));
            assert_eq!(r.recv().await, Some(3));
        });
    }


    #[test]
    fn work() {
        async fn value() -> String {
            String::from("233\n666")
        }

        async fn call() {
            value().await.lines().for_each(
                |line| println!("{}", line)
            )
        }

        task::block_on(call())
    }
}

