struct Salary {
    income: Vec<u32>,
    tax_exclude: Vec<u32>,
}

struct TaxStep {
    ceil: u32,
    rate: u32,
    deduct: u32,
}

fn get_matched_tax_step(steps: &Vec<TaxStep>, salary_count: u32) -> Option<&TaxStep> {
    steps.iter().find(|step| salary_count <= step.ceil)
}

fn main() {
    let tax_steps = vec![
        TaxStep {
            ceil: 36000,
            rate: 3,
            deduct: 0,
        },
        TaxStep {
            ceil: 144000,
            rate: 10,
            deduct: 2520,
        },
        TaxStep {
            ceil: 300000,
            rate: 20,
            deduct: 16920,
        },
        TaxStep {
            ceil: 420000,
            rate: 25,
            deduct: 31920,
        },
        TaxStep {
            ceil: 660000,
            rate: 30,
            deduct: 52920,
        },
        TaxStep {
            ceil: 960000,
            rate: 35,
            deduct: 85920,
        },
        TaxStep {
            ceil: 999999999,
            rate: 45,
            deduct: 181920,
        },
    ];

    let salary_list = vec![
        Salary {
            income: vec![20000, 500, 550],
            tax_exclude: vec![3500, 5000],
        },
        Salary {
            income: vec![20000, 500, 450],
            tax_exclude: vec![3500, 2000, 5000],
        },
        Salary {
            income: vec![20000, 500, 500],
            tax_exclude: vec![3500, 1000, 5000],
        },
        Salary {
            income: vec![23100, 7000, 500],
            tax_exclude: vec![3920, 1000, 5000],
        },
        Salary {
            income: vec![23100, 310, 125],
            tax_exclude: vec![3920, 1000, 5000],
        },
        Salary {
            income: vec![23100, 500],
            tax_exclude: vec![3920, 1000, 5000],
        },
        Salary {
            income: vec![23100, 10066, 500],
            tax_exclude: vec![3920, 1000, 5000],
        },
        Salary {
            income: vec![23100, 500, 75],
            tax_exclude: vec![3920, 1000, 5000],
        },
        Salary {
            income: vec![23100, 500, 175],
            tax_exclude: vec![3920, 1000, 5000],
        },
        Salary {
            income: vec![23100, 500, 75],
            tax_exclude: vec![3920, 1000, 5000],
        },
        Salary {
            income: vec![23100, 500, 75],
            tax_exclude: vec![3920, 1000, 5000],
        },
    ];

    let mut tax_count = 0u32;
    let mut income_count = 0u32;
    let mut tax_exclude_count = 0u32;
    let mut tax_list: Vec<u32> = Vec::new();

    for salary in salary_list.iter() {
        let income_sum: u32 = salary.income.iter().sum();
        income_count += income_sum;

        let tax_exclude_sum: u32 = salary.tax_exclude.iter().sum();
        tax_exclude_count += tax_exclude_sum;

        let tax_income_count = income_count - tax_exclude_count;
        let step = get_matched_tax_step(&tax_steps, tax_income_count).unwrap();
        let tax = tax_income_count * step.rate / 100 - step.deduct - tax_count;
        tax_list.push(tax);

        tax_count += tax
    }

    println!("{:?}", tax_list)
}
