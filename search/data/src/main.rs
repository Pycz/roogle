extern crate debug;
extern crate rustdoc;
extern crate rustc;
extern crate syntax;
extern crate serialize;
extern crate getopts;

use std::os;
use rustdoc::core;
use rustdoc::clean;
use std::collections::HashMap;
use serialize::json::{Json, ToJson, List};
use rustdoc::fold::DocFolder;
use getopts::{optflag,getopts,OptGroup};
use cache::JsonFolder;
mod cache;
mod json;


fn print_usage(program: &str, opts: &[OptGroup]) {
    println!("Usage: {} <source>", program);
    for opt in opts.iter() {
        println!("-{} --{}\t{}", opt.short_name, opt.long_name, opt.desc);
    }
}


fn main() {
    let args: Vec<String> = os::args();
    let program = args[0].clone();
    let opts = [
        optflag("h", "help", "Print this help menu"),
    ];

    let matches = match getopts(args.tail(), opts) {
        Ok(m) => m,
        Err(f) => fail!(f.to_string())
    };

    if matches.opt_present("h") || matches.free.len() != 1 {
        print_usage(program.as_slice(), opts);
        return;
    }

    let cratefile = &matches.free[0];
    let cr = Path::new(cratefile.as_slice());
    let libs: Vec<Path> = vec![Path::new(
        "/usr/local/lib/rustlib/i686-unknown-linux-gnu/lib")];
    let cfgs: Vec<String> = vec![];
    let externs: core::Externs = HashMap::new();
    println!("starting to run rustc");
    let (mut krate, analysis) = std::task::try(proc() {
        let cr = cr;
        core::run_core(libs, cfgs, externs, &cr, None)
    }).map_err(|boxed_any|format!("{:?}", boxed_any)).unwrap();
    println!("finished with rustc");

    let mut cache = cache::Cache {
        public_item: krate.module.clone(),
        typarams: analysis.external_typarams.borrow_mut().take().unwrap(),
    };

    cache.fold_crate(krate);
    cache::cache_key.replace(Some(cache.clone()));

    let js = json::RItem(cache.public_item.clone().unwrap()).to_json();
    println!("{}", cache.fold_json(&js));
}
