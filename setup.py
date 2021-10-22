import os

from setuptools import setup, find_packages
from setuptools.command.sdist import sdist
from setuptools.command.install import install
from setuptools.command.develop import develop


def _read_file(path: str) -> str:
    with open(path) as f:
        return f.read().strip()


# Versions
antlr_version = '4.8'
file_dir = os.path.dirname(os.path.realpath(__file__))
cloak_version = _read_file(os.path.join(file_dir, 'cloak', 'VERSION'))
packages = find_packages()


def build_grammar():
    source_dir = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'cloak')
    antlr_jar_name = f'antlr-{antlr_version}.jar'
    antlr_jar_path = os.path.join(source_dir, 'solidity_parser', antlr_jar_name)
    if not os.path.exists(antlr_jar_path):
        # Download antlr if necessary
        import urllib.request
        urllib.request.urlretrieve(f'https://www.antlr.org/download/antlr-{antlr_version}-complete.jar', antlr_jar_path)
    import subprocess
    subprocess.check_call(['java', '-jar', antlr_jar_name, '-o', 'generated', '-visitor', '-Dlanguage=Python3', 'Solidity.g4'],
                          cwd=os.path.dirname(os.path.realpath(antlr_jar_path)))


def install_latest_compatible_solc():
    import solcx
    from cloak.config_version import Versions
    solcx.install_solc_pragma(Versions.CLOAK_SOLC_VERSION_COMPATIBILITY.expression)


class CustomSdist(sdist):
    def run(self):
        build_grammar()
        sdist.run(self)


class CustomInstall(install):
    def run(self):
        install.run(self)
        install_latest_compatible_solc()


class CustomDevelop(develop):
    def run(self):
        build_grammar()
        develop.run(self)
        install_latest_compatible_solc()


setup(
    # Metadata
    name='cloak',
    version=cloak_version,
    author='Nick Baumann, SRI Lab ETH Zurich',
    # url='https://github.com/eth-sri/zkay',
    license='MIT',
    description='Cloak is a programming language which enables automatic compilation of intuitive data privacy specifications to Ethereum smart contracts leveraging encryption and non-interactive zero-knowledge (NIZK) proofs. The zkay package provides a toolchain for compiling, deploying and using zkay contracts.',

    # Dependencies
    python_requires='>=3.7,<4',
    # install_requires=[
    #     'Cython>=0.29,<0.30',
    #     'web3[tester]>=v5.11,<v5.13',
    #     f'antlr4-python3-runtime=={antlr_version}',
    #     'parameterized>=0.7,<0.8',
    #     'py-solc-x',
    #     'pycryptodome>=3.9,<4',
    #     'appdirs>=1.4,<1.5',
    #     'argcomplete>=1,<2',
    #     'semantic-version>=2.8.4,<2.9',

    #     'pysha3>=1.0.2,<1.1', # Console script doesn't work without this even though it is not required
    #     'ccf==1.0.3',
    # ],

    # Contents
    packages=packages,
    include_package_data=True,
    entry_points={
        "console_scripts": [
            "cloak=cloak.__main__:main"
        ]
    },

    # Build steps
    cmdclass={
        'sdist': CustomSdist,
        'install': CustomInstall,
        'develop': CustomDevelop
    }
)
