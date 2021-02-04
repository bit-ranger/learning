use std::fs::File;
use std::io::{BufReader, BufRead, Error};

fn main() {
    // let _ = gen_count();
    let _ = gen_update();
}

fn gen_update() -> Result<(), Error> {

    let column_list = vec![
        "table_name,code_column,name_column",
    ];

    for column in column_list {
        let column_def:Vec<&str> = column.split(",").collect();
        let file:File= File::open("path to csv")?;
        let reader = BufReader::new(&file);

        for line in reader.lines() {
            let l = line?;
            let record = l.split(",").collect();
            let sql = to_update_sql(column_def[0], column_def[1], column_def[2], record);
            println!("{}", sql);
        }
    }



    return Result::Ok(());
}

fn to_update_sql(table: &str, code_column: &str, name_column: &str, record: Vec<&str>) -> String{
    let origin_org_code = record[0];
    return
        if name_column.is_empty(){
            format!("update {} set {} = '{}' where {} = '{}';", table, code_column, record[2], code_column, origin_org_code)
        } else {
            format!("update {} set {} = '{}', {} = '{}' where {} = '{}';", table, code_column, record[2], name_column, record[3], code_column, origin_org_code)
        }
}

fn gen_count(){
    let table_list = vec![
        //table name
    ];

    for table in table_list {
        let sql = to_count_sql(table);
        println!("{}", sql);
    }
}

fn to_count_sql(table: &str) -> String{
    return format!("select count(*) from {};", table);
}


