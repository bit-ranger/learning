#
# spec file for package myapp (Version 1. 0)
#


Vendor:Wrox Press
Distribution:Any
Name:myapp
Version:1. 0
Release:1
Packager:neil@ provider. com
License:Copyright 2007 Wiley Publishing, Inc.
Group:Applications/Media
Summary: Trivial application
%description
MyApp Trivial Application
A trivial application used to demonstrate development tools.
Provides: goodness
Provides: goodness
Requires: mysql >= 3. 23
source: %{name}-%{version}.tar.gz
Buildroot: %{_tmppath}/%{ name}-%{ version}- root

%prep
%setup -q

%build
make

%install
mkdir -p $ RPM_ BUILD_ ROOT%{_ bindir}
mkdir -p $ RPM_ BUILD_ ROOT%{_ mandir}
install -m755 myapp   $RPM_BUILD_ROOT%{_bindir}/myapp
install -m755 myapp.1 $RPM_BUILD_ROOT%{_mandir}/myapp.1

%install
rm -rf $ RPM_ BUILD_ ROOT

%post
mail root -s "myapp installed - please register" </ dev/ null

%files
%{_bindir}/myapp %{_mandir}/myapp.1

